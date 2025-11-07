package com.taskboard.controller;

import com.taskboard.common.Result;
import com.taskboard.dto.LoginRequest;
import com.taskboard.dto.LoginResponse;
import com.taskboard.dto.RegisterRequest;
import com.taskboard.dto.UserResponse;
import com.taskboard.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 * 处理用户注册、登录等认证相关接口
 *
 * @author 哈雷酱
 * @date 2025
 */
@Tag(name = "认证管理", description = "用户注册、登录等认证相关接口")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * 用户注册
     */
    @Operation(summary = "用户注册", description = "注册新用户账号")
    @PostMapping("/register")
    public Result<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse response = userService.register(request);
        return Result.success(response, "注册成功");
    }

    /**
     * 用户登录
     */
    @Operation(summary = "用户登录", description = "用户登录获取Token")
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return Result.success(response, "登录成功");
    }

    /**
     * 获取当前登录用户信息
     */
    @Operation(summary = "获取当前用户信息", description = "根据Token获取当前登录用户信息")
    @GetMapping("/me")
    public Result<UserResponse> getCurrentUser(@RequestAttribute("username") String username) {
        UserResponse response = userService.getUserByUsername(username);
        return Result.success(response);
    }
}
