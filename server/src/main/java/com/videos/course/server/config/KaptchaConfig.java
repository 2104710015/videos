package com.videos.course.server.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.util.Properties;
import java.util.Random;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-05-06 16:33
 */
@Configuration
public class KaptchaConfig {

        @Bean
        public DefaultKaptcha getDefaultKaptcha() {
            DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
            Properties properties = new Properties();
            properties.setProperty("kaptcha.border", "no");
            //    properties.setProperty("kaptcha.border.color", "105,179,90");
            properties.setProperty("kaptcha.textproducer.font.color", "blue");
            properties.setProperty("kaptcha.image.width", "90");
            properties.setProperty("kaptcha.image.height", "32");
            properties.setProperty("kaptcha.textproducer.font.size", "24");
            properties.setProperty("kaptcha.session.key", "code");
            properties.setProperty("kaptcha.textproducer.char.length", "4");
            properties.setProperty("kaptcha.textproducer.font.names", "Arial");
            properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
            properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");
            Config config = new Config(properties);
            defaultKaptcha.setConfig(config);
            return defaultKaptcha;
        }

        @Bean
        public DefaultKaptcha getWebKaptcha() {
            DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
            Properties properties = new Properties();
            properties.setProperty("kaptcha.border", "no");
//        properties.setProperty("kaptcha.border.color", "105,179,90");
            properties.setProperty("kaptcha.textproducer.font.color", "blue");
            properties.setProperty("kaptcha.image.width", "90");
            properties.setProperty("kaptcha.image.height", "45");
            properties.setProperty("kaptcha.textproducer.font.size", "30");
            properties.setProperty("kaptcha.session.key", "code");
            properties.setProperty("kaptcha.textproducer.char.length", "4");
            properties.setProperty("kaptcha.textproducer.font.names", "Arial");
            properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
            properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");
            Config config = new Config(properties);
            defaultKaptcha.setConfig(config);
            return defaultKaptcha;
        }
    }
