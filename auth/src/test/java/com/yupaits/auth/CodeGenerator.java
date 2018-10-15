package com.yupaits.auth;

import com.yupaits.generator.AbstractCodeGenerator;

/**
 * @author yupaits
 * @date 2018/10/15
 */
public class CodeGenerator extends AbstractCodeGenerator {

    /**
     * @param moduleName 模块名称，为空时需要在控制台手动输入
     * @param outputDir  文件输出路径，为空时使用默认路径
     */
    public CodeGenerator(String moduleName, String outputDir) {
        super(moduleName, outputDir);
    }

    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator("auth", null);
        generator.generate();
    }
}
