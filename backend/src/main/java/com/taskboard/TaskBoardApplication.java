package com.taskboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 任务看板系统启动类
 *
 * @author 哈雷酱
 * @date 2025
 */
@SpringBootApplication
public class TaskBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskBoardApplication.class, args);
        System.out.println("""

            ========================================
            ✨ 任务看板系统启动成功！
            📋 API文档地址: http://localhost:8080/api/doc.html
            🚀 系统就绪，开始你的高效协作之旅！
            ========================================
            """);
    }

}
