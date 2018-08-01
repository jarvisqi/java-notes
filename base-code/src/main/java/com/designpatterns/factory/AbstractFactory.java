package com.designpatterns.factory;


/**
 * 抽象工厂
 *
 * @author Jarvis
 * @date 2018/7/26
 */
public class AbstractFactory {

    public static void main(String[] args) {
        // step 1,选定一个“大厂”
        ComputerFactory intelFactory = new IntelFactory();
        // step 2,造cpu
        var intelCpu = intelFactory.createCPU();
        // step 2,造MainBoard
        var intelMainBoard = intelFactory.createMainBoard();
        // step 3,组装电脑
        var intelComputer = new Computer(intelCpu, intelMainBoard);
        var intelComputerInfo = intelComputer.toString();
        // step 4,输出信息
        System.out.println(intelComputerInfo);

        // step 1,选定一个“大厂”
        ComputerFactory amdFactory = new AmdFactory();
        // step 2,造cpu
        var amdcpu = amdFactory.createCPU();
        // step 2,造MainBoard
        var amdMainBoard = amdFactory.createMainBoard();
        // step 3,组装电脑
        var amdComputer = new Computer(amdcpu, amdMainBoard);
        var amdComputerInfo = amdComputer.toString();
        // step 4,输出信息
        System.out.println(amdComputerInfo);

    }
}

/**
 * 电脑工厂 抽象接口
 */
interface ComputerFactory {
    /**
     * 造CPU
     *
     * @return
     */
    Cpu createCPU();

    /**
     * 造主板
     *
     * @return
     */
    MainBoard createMainBoard();
}

/**
 * Intel 工厂
 */
class IntelFactory implements ComputerFactory {

    /**
     * 造CPU
     *
     * @return
     */
    @Override
    public Cpu createCPU() {
        return new IntelCpu("Intel工厂制造 -intel 处理器");
    }

    /**
     * 造主板
     *
     * @return
     */
    @Override
    public MainBoard createMainBoard() {
        return new IntelMainBoard("Intel工厂制造 -intel 主板");
    }
}


/**
 * Amd 工厂
 */
class AmdFactory implements ComputerFactory {

    /**
     * 造CPU
     *
     * @return
     */
    @Override
    public Cpu createCPU() {
        return new AmdCpu("Amd工厂制造 -amd 处理器");
    }

    /**
     * 造主板
     *
     * @return
     */
    @Override
    public MainBoard createMainBoard() {
        return new AmdMainBoard("Amd工厂制造 -amd 主板");
    }
}

/**
 * 计算机
 */
class Computer {
    private Cpu cpu;
    private MainBoard mainBoard;

    /**
     * 组装电脑
     *
     * @param cpu
     * @param mainBoard
     */
    public Computer(Cpu cpu, MainBoard mainBoard) {
        this.cpu = cpu;
        this.mainBoard = mainBoard;
    }

    @Override
    public String toString() {
        return cpu.getName() + mainBoard.getName();
    }
}


class Cpu {
    public Cpu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private String name;
}

class IntelCpu extends Cpu {

    public IntelCpu(String name) {
        super(name);
    }
}

class AmdCpu extends Cpu {

    public AmdCpu(String name) {
        super(name);
    }
}


class MainBoard {
    public MainBoard(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private String name;
}

class IntelMainBoard extends MainBoard {

    public IntelMainBoard(String name) {
        super(name);
    }
}

class AmdMainBoard extends MainBoard {

    public AmdMainBoard(String name) {
        super(name);
    }
}