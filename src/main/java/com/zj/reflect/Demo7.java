package com.zj.reflect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

public class Demo7 {
    /**
     * Result类型，这个类型可作为任何接口通用的返回值类型
     *
     * @param <T>
     */
    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result<T> implements Serializable { //@1
        private String code; //状态码
        private String subCode; //子状态码
        private String msg; //接口返回的提示信息（如错误提示，操作成功等）
        private T data; //任何类型的数据，具体类型可在创建Result时指定
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserModel { //@2 创建一个用户类
        private Integer id;
        private String name;
    }

    /**
     * 返回一个用户信息
     *
     * @return
     */
    public static Result<UserModel> getUser() { //@3 模拟返回一个用户信息，用户信息封装在Result中(的data)。
        UserModel userModel = UserModel.builder().id(1).name("路人甲Java").build();
        Result<UserModel> result = Result.<UserModel>builder().code("1").subCode(null).msg("操作成功").data(userModel).build();
        return result;
    }

    /**
     * 用户json信息
     *
     * @return
     */
    public static String getUserString() { //@4 将用户信息转换为json字符串返回
        return JSON.toJSONString(getUser());
    }

    public static void main(String[] args) {
        String userString = getUserString();
        //会输出：{"code":"1","data":{"id":1,"name":"路人甲Java"},"msg":"操作成功"}
        System.out.println(userString); //@5

        //下面将userString反序列化为Result<UserModel>对象
        Result<UserModel> userModelResult = JSON.parseObject(userString, new TypeReference<Result<UserModel>>() {
        }); //@6 fastjson如何获取泛型类Result中T的具体的类型？T具体的类型是UserModel
        // 关键：new TypeReference<Result<UserModel>>() {}，创建了一个TypeReference类的子类（其实匿名内部类）

        //我们来看看Result中的data是不是UserModel类型的
        System.out.println(userModelResult.getData().getClass()); //@6
    }
}