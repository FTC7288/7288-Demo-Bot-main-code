#pragma once

#include "../Util.hpp"


class Dc_Motor_Ex {
private:
    jobject dcMotorSimple;
    jclass jclazz;
    jmethodID setPowerMethod;
public:
    explicit Dc_Motor_Ex(jobject motor) noexcept;

    ~Dc_Motor_Ex();


    void setPower(double power);
};




