package com.wenhao.calculator.calculator;

import com.wenhao.calculator.calculator.dto.CalculateDto;
import com.wenhao.calculator.calculator.dto.DoubleSuitDto;
import com.wenhao.calculator.calculator.dto.SuitDto;
import com.wenhao.calculator.calculator.vo.CalculateResultVo;
import com.wenhao.calculator.calculator.vo.CalculateVo;
import com.wenhao.calculator.common.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public Response<CalculateVo> calculate(@RequestBody CalculateDto calculateDto) {
        return new Response<>(
                calculatorService.calculate(
                        calculateDto.getName(),
                        calculateDto.getWeapon(),
                        calculateDto.getArtifacts()));
    }

    @RequestMapping(value = "suit", method = POST, produces = "application/json")
    public Response<CalculateResultVo> selectSuitArtifacts(@RequestBody SuitDto suitDto) {
        return new Response<>(
                calculatorService.selectSuitArtifacts(
                        suitDto.getSuit(),
                        suitDto.getWeapon(),
                        suitDto.getKeywords(),
                        suitDto.getName()));
    }

    @RequestMapping(value = "doubleSuit", method = POST, produces = "application/json")
    public Response<CalculateResultVo> selectDoubleSuitArtifacts(@RequestBody DoubleSuitDto doubleSuitDto) {
        return new Response<>(
                calculatorService.selectDoubleSuitArtifacts(
                        doubleSuitDto.getSuitKeyword1(),
                        doubleSuitDto.getSuitKeyword2(),
                        doubleSuitDto.getWeapon(),
                        doubleSuitDto.getKeywords(),
                        doubleSuitDto.getName()));
    }
}
