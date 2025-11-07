package com.taskboard.service;

import com.taskboard.dto.LoginRequest;
import com.taskboard.dto.LoginResponse;
import com.taskboard.dto.RegisterRequest;
import com.taskboard.dto.UserResponse;
import com.taskboard.entity.User;
import com.taskboard.exception.BusinessException;
import com.taskboard.repository.UserRepository;
import com.taskboard.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务类
 * 处理用户注册、登录、信息管理等业务逻辑
 *
 * @author 哈雷酱
 * @date 2025
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 用户注册
     *
     * @param request 注册请求
     * @return 用户响应
     */
    @Transactional(rollbackFor = Exception.class)
    public UserResponse register(RegisterRequest request) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("用户名已存在");
        }

        // 检查邮箱是否已存在
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new BusinessException("邮箱已被注册");
            }
        }

        // 创建用户对象
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // 加密密码
        user.setNickname(request.getNickname() != null ? request.getNickname() : request.getUsername());
        user.setEmail(request.getEmail());
        user.setStatus(1); // 默认启用
        user.setDeleted(0); // 默认未删除

        // 保存用户
        User savedUser = userRepository.save(user);

        // 转换为响应对象
        return convertToUserResponse(savedUser);
    }

    /**
     * 用户登录
     *
     * @param request 登录请求
     * @return 登录响应（包含Token和用户信息）
     */
    public LoginResponse login(LoginRequest request) {
        // 查找用户
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException("用户名或密码错误"));

        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 检查用户状态
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }

        // 生成JWT Token
        String token = jwtUtil.generateToken(user.getUsername(), user.getId());

        // 返回登录响应
        UserResponse userResponse = convertToUserResponse(user);
        return new LoginResponse(token, userResponse);
    }

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户响应
     */
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("用户不存在"));
        return convertToUserResponse(user);
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户响应
     */
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException("用户不存在"));
        return convertToUserResponse(user);
    }

    /**
     * 将User实体转换为UserResponse
     *
     * @param user 用户实体
     * @return 用户响应DTO
     */
    private UserResponse convertToUserResponse(User user) {
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }
}
