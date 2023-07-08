package com.wenhao.calculator.calculator;

import com.alibaba.excel.EasyExcel;
import com.wenhao.calculator.calculator.dto.CalculateDto;
import com.wenhao.calculator.calculator.dto.DoubleSuitDto;
import com.wenhao.calculator.calculator.dto.SuitDto;
import com.wenhao.calculator.calculator.vo.CalculateResultVo;
import com.wenhao.calculator.calculator.vo.CalculateVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * CalculatorController
 *
 * @author: Wenhao Tong
 * @date: 2023/6/20
 */
@RestController
@RequestMapping("/calculator")
@ResponseBody
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @RequestMapping(value = "calculate", method = POST, produces = "application/json")
    public CalculateVo calculate(@RequestBody CalculateDto calculateDto) {
        return calculatorService.calculate(
                        calculateDto.getName(),
                        calculateDto.getWeapon(),
                        calculateDto.getArtifacts());
    }

    @RequestMapping(value = "suit", method = POST, produces = "application/json")
    public CalculateResultVo selectSuitArtifacts(@RequestBody SuitDto suitDto) {
        return calculatorService.selectSuitArtifacts(
                        suitDto.getSuit(),
                        suitDto.getWeapon(),
                        suitDto.getKeywords(),
                        suitDto.getName());
    }

    @RequestMapping(value = "doubleSuit", method = POST, produces = "application/json")
    public CalculateResultVo selectDoubleSuitArtifacts(@RequestBody DoubleSuitDto doubleSuitDto) {
        return calculatorService.selectDoubleSuitArtifacts(
                        doubleSuitDto.getSuitKeyword1(),
                        doubleSuitDto.getSuitKeyword2(),
                        doubleSuitDto.getWeapon(),
                        doubleSuitDto.getKeywords(),
                        doubleSuitDto.getName());
    }

    @RequestMapping(value = "getExcel", method = GET)
    private void getExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        String fileName = URLEncoder.encode("伤害计算表格", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), CalculateResultVo.class).sheet("伤害计算表格").doWrite(calculatorService.getExcel());
    }
}
