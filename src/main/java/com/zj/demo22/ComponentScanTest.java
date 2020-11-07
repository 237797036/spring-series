package com.zj.demo22;

import com.zj.demo22.test1.ScanBean1;
import com.zj.demo22.test2.ScanBean2;
import com.zj.demo22.test3.ScanBean3;
import com.zj.demo22.test4.ScanBean4;
import com.zj.demo22.test5.ScanBean5;
import com.zj.demo22.test6.ScanBean6;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 注解@ComponentScan：Spring扫描指定的包及子包，得到一批类，再经过一些过滤器，最后剩下的类会被注册到容器中。
 *
 * 主要关注2个问题：
 *
 * 第一个：需要扫描哪些包？通过value、backPackages、basePackageClasses这3个参数控制
 * 第二：过滤器有哪些？通过useDefaultFilters、includeFilters、excludeFilters这3个参数控制过滤器
 *
 * 默认情况，将@ComponentScan修饰的类所在的包作为扫描包；
 * useDefaultFilters为true，使用默认过滤器。凡是类上有@Repository、@Service、@Controller、@Component，会被注册到spring容器。
 *
 * Repository上有@Component注解。设置Repository的value时，相当于给@Component的value设置值。
 * 注解@Service、@Controller和@Repository类似。这4个注解没有任何差别，都采用@Component方式解析。通常根据系统分层选择。
 *
 */

public class ComponentScanTest {

    /**
     * 默认会扫描ScanBean类所在的包及子包中的所有类（包括ScanBean1类本身）
     * 类上有@Component、@Repository、@Service、@Controller都会被注册到容器
     */
	@Test
	public void test1() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanBean1.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(beanName + "->" + context.getBean(beanName));
		}
	}

	// 指定需要扫毛哪些包：可通过value或basePackage配置，二选一
	// 但指定包名的方式，存在一个隐患，若包被重命名，会导致扫描失效
	@Test
	public void test2() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanBean2.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(beanName + "->" + context.getBean(beanName));
		}
	}

	// 一般使用basePackageClasses方式指定需要扫描的包，此参数可指定一些类型，默认会扫描这些类所在的包及其子包中所有的类，
    // 这种方式可有效避免包重命名问题
	@Test
	public void test3() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanBean3.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(beanName + "->" + context.getBean(beanName));
		}
	}

	@Test
	public void test4() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanBean4.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(beanName + "->" + context.getBean(beanName));
		}
	}

	@Test
	public void test5() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanBean5.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(beanName + "->" + context.getBean(beanName));
		}
	}

	@Test
	public void test6() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanBean6.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(beanName + "->" + context.getBean(beanName));
		}
	}
}
