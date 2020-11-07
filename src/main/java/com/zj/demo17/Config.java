package com.zj.demo17;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public C1 c1() {
        return new C1();
    }

    @Bean
    public C2 c2() {
        C1 c1 = c1(); //@1
        return new C2(c1);
    }

    @Bean
    public C3 c3() {
        C1 c1 = c1(); //@2
        return new C3(c1);
    }

    public static class C1 {
    }

    public static class C2 {
        private C1 c1;

        public C2(C1 c1) {
            this.c1 = c1;
        }

        @Override
        public String toString() {
            return "C2{" +
                    "c1=" + c1 +
                    '}';
        }
    }

    public static class C3 {
        private C1 c1;

        public C3(C1 c1) {
            this.c1 = c1;
        }

        @Override
        public String toString() {
            return "C3{" +
                    "c1=" + c1 +
                    '}';
        }
    }

}
