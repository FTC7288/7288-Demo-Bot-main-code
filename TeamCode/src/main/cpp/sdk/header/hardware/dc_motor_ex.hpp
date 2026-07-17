#pragma once

#include "../util.hpp"


class dc_motor_ex {
private:
    jobject dcMotorSimple;
    jclass jclazz;
    jmethodID setPowerMethod;
public:
    explicit dc_motor_ex(jobject motor) noexcept;

    ~dc_motor_ex();

    void setPower(double power);
};




