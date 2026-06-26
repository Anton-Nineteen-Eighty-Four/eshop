package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.domain.User;

public interface MailSenderService {
    void sendActivateCode(User user);
}
