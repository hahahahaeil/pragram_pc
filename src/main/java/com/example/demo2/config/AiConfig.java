package com.example.demo2.config;

import com.example.demo2.service.ToolsService;
import dev.langchain4j.community.model.dashscope.QwenEmbeddingModel;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByCharacterSplitter;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.*;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.util.List;

@Configuration
public class AiConfig {

    // 1. 注册 FileChatMemoryStore
    @Bean
    public ChatMemoryStore fileChatMemoryStore() {
        return new FileChatMemoryStore();
    }

    @Bean
    public EmbeddingStore embeddingStore() {
        return new InMemoryEmbeddingStore();
    }

    @Bean
    public QwenEmbeddingModel qwenEmbeddingModel() {
// 使用建造者模式配置模型参数
        QwenEmbeddingModel embeddingModel = QwenEmbeddingModel.builder()
                .apiKey("sk-bcadc0f262e54dee9e5a6b69691d5e2a")
                .build();  // 完成模型构建
        return embeddingModel;
    }
//    sk-bcadc0f262e54dee9e5a6b69691d5e2a
    // 定义AI助手的公共接口
    public interface Assistant{
        // 普通聊天方法（阻塞式，返回完整响应）带聊天记忆功能
        String chat(@MemoryId String memoryId, @UserMessage String message);
        // 流式响应方法（实时返回生成的token）带聊天记忆功能
        @SystemMessage("""

 你是一个计算机专业课视频推荐助手。主要面向计算机科学与技术、软件工程及相关专业的学生。你的任务是根据学生的学习阶段、课程基础和学习目标，提供合理、清晰且具有专业依据的课程学习建议。
                                                                            
                                      你的回答应当结构清晰、简洁，优先使用列表和分点说明。
                                      
                                      每次推荐课程数量不超过 5 个。
                                      
                                      每个课程必须包含一个可点击的视频或官网链接。
                                      
                                      避免输出过长的理论说明，如需扩展应征求用户同意。
               
                                      在回答问题时，应以计算机专业课程体系为基础，重点参考数据结构、操作系统、计算机网络、数据库原理、软件工程、计算机组成原理以及人工智能等核心课程内容。推荐课程时需说明课程之间的先后依赖关系，例如在学习操作系统之前应具备一定的数据结构和计算机组成原理基础。
                
                                      回答应注重概念准确性和逻辑清晰性，避免口语化和主观情绪表达，优先使用教材式或教学型语言。在涉及学习路径时，应结合理论学习与实践能力培养，合理区分基础课程、核心课程和进阶课程。
                
                                      当学生提出与课程选择、学习顺序、知识衔接或方向规划相关的问题时，应给出结构化建议，必要时可列出课程列表或学习阶段划分，但不应虚构不存在的课程或不严谨的专业结论。

                       """)
        TokenStream stream(@MemoryId String memoryId, @UserMessage String message);
    }

    // 定义Spring Bean来创建助手实例
    @Bean  // 标记为Spring管理的Bean
    public Assistant getAssistant(// 注入标准聊天语言模型（用于普通响应）
                                  ChatLanguageModel chatLanguageModel,
// 注入自定义的聊天记录持久化方案
                                  ChatMemoryStore chatMemoryStore,
// 注入流式聊天语言模型（用于流式响应）
                                  StreamingChatLanguageModel streamingChatLanguageModel,
                                  EmbeddingStore embeddingStore,
                                  QwenEmbeddingModel qwenEmbeddingModel,
//    工具类
                                  ToolsService toolsService
    ){

// 内容检索器
        ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(qwenEmbeddingModel)
                .maxResults(5) // 最相似的5个结果
                .minScore(0.6) // 只找相似度在0.6以上的内容
                .build();

// 使用AI服务构建助手实现
        Assistant assistant = AiServices.builder(Assistant.class)
//                工具类
                .tools(toolsService)
// 设置标准聊天模型（用于chat()方法）
                .chatLanguageModel(chatLanguageModel)
// 设置流式聊天模型（用于stream()方法）
                .streamingChatLanguageModel(streamingChatLanguageModel)
// 配置对话记忆提供者（为每个memoryId创建独立的记忆窗口）
                .chatMemoryProvider(memoryId ->
                                MessageWindowChatMemory.builder()
                                        .maxMessages(10)
                                        .id(memoryId)
// 使用自定义的持久化方案
                                        .chatMemoryStore(chatMemoryStore)
                                        .build()
                )
                .contentRetriever(contentRetriever)
// 完成构建
                .build();
// 返回配置好的助手实例
        return assistant;
    }


    @Bean
    CommandLineRunner ingestTermOfServiceToVectorStore(QwenEmbeddingModel qwenEmbeddingModel, EmbeddingStore embeddingStore)
            throws URISyntaxException {
        Document document = ClassPathDocumentLoader.loadDocument("rag/rag-service.txt",new TextDocumentParser());
        return args->{
            DocumentByCharacterSplitter splitter = new DocumentByCharacterSplitter(200, 50);
            List<TextSegment> segments = splitter.split(document);
            List<Embedding> embeddings = qwenEmbeddingModel.embedAll(segments).content();
            embeddingStore.addAll(embeddings,segments);
        };

    }
}
