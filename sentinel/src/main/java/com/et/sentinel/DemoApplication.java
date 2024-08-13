package com.et.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		// 配置规则.
		initFlowRules();
		/*while (true) {
			// 1.5.0 版本开始可以直接利用 try-with-resources 特性
			try (Entry entry = SphU.entry("HelloWorld")) {
				// 被保护的逻辑
				Thread.sleep(300);
				System.out.println("hello world");
			} catch (BlockException | InterruptedException ex) {
				// 处理被流控的逻辑
				System.out.println("blocked!");
			}
		}*/

	}

	private static void initFlowRules(){
		List<FlowRule> rules = new ArrayList<>();
		FlowRule rule = new FlowRule();
		rule.setResource("HelloWorld");
		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		// Set limit QPS to 20.
		rule.setCount(2);
		rules.add(rule);
		FlowRuleManager.loadRules(rules);
	}

}
