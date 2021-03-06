package Controller;

import Entity.Controller.ICalculatorOperator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Stack;
import java.util.regex.Pattern;

public class Calculator implements ICalculatorOperator
{
    private final Stack<BigDecimal> operand;
    private BigDecimal remNum;
    private final Pattern pattern;


    public Calculator()
    {
        remNum = BigDecimal.ZERO;
        operand = new Stack<>();
        pattern = Pattern.compile("-?\\d+(\\.\\d+)?"); //Already avoid scientific number
    }

    @Override
    public void reset()
    {
        operand.clear();
    }

    @Override
    public void append(String data)
    {
        if (pattern.matcher(data).matches())
            operand.add(new BigDecimal(data));
    }

    @Override
    public String sqrt()
    {
        BigDecimal tmp = operand.pop();

        if (tmp.compareTo(BigDecimal.ZERO) < 0)
            return "ERROR";
        return tmp.sqrt(new MathContext(10)).stripTrailingZeros().toPlainString();
    }

    @Override
    public String flip()
    {
        BigDecimal tmp = operand.pop();
        if (tmp.equals(BigDecimal.ZERO))
            return "ERROR";
        return BigDecimal.ONE.divide(tmp, 10, RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString();
    }

    @Override
    public String add()
    {
        BigDecimal tmp1 = operand.pop();
        BigDecimal tmp2 = operand.pop();

        return tmp1.add(tmp2).stripTrailingZeros().toPlainString();
    }

    @Override
    public String subtract()
    {
        BigDecimal tmp1 = operand.pop();
        BigDecimal tmp2 = operand.pop();

        return tmp2.subtract(tmp1).stripTrailingZeros().toPlainString();
    }

    @Override
    public String multiply()
    {
        BigDecimal tmp1 = operand.pop();
        BigDecimal tmp2 = operand.pop();

        return tmp1.multiply(tmp2).stripTrailingZeros().toPlainString();
    }

    @Override
    public String divide()
    {
        BigDecimal tmp1 = operand.pop();
        BigDecimal tmp2 = operand.pop();
        if (tmp1.equals(BigDecimal.ZERO)) return "ERROR";
        return tmp2.divide(tmp1, 4, RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString();
    }

    @Override
    public String readMem()
    {
        return remNum.stripTrailingZeros().toPlainString();
    }

    @Override
    public void clrMem()
    {
        remNum = BigDecimal.ZERO;
    }

    @Override
    public void memAdd()
    {
        remNum = remNum.add(operand.pop());
    }

    @Override
    public void memSub()
    {
        remNum = remNum.subtract(operand.pop());
    }

    @Override
    public String percent()
    {
        BigDecimal tmp = operand.pop();
        return tmp.divide(BigDecimal.TEN.multiply(BigDecimal.TEN),MathContext.DECIMAL64).stripTrailingZeros().toPlainString();
    }

    @Override
    public String negate()
    {
        BigDecimal tmp = operand.pop();
        return tmp.negate().stripTrailingZeros().toPlainString();
    }
}

