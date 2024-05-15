package ru.task.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PageController {

    @GetMapping("/task-management")
    public String getBoardListPage() {
        return "home";
    }

    @GetMapping("/task-management/board/{id}")
    public String geBoardPage(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "board";
    }

    @GetMapping("/task-management/task/{id}")
    public String getTaskPage(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "task";
    }

    @GetMapping("/task-management/board/{boardId}/task/new")
    public String getNewTaskPage(@PathVariable String boardId, Model model) {
        model.addAttribute("boardId", boardId);
        return "task";
    }

    @GetMapping("/task-management/board/config/{boardId}")
    public String getBordConfigPage(@PathVariable String boardId, Model model) {
        model.addAttribute("boardId", boardId);
        return "boardConfiguration";
    }

    @GetMapping("/task-management/board/new")
    public String getNewBordConfigPage() {
        return "boardConfiguration";
    }
}
