#pragma once

#include "util.hpp"


class op_mode
{
public:
    virtual ~op_mode() = default;

    virtual void native_init() = 0;

    virtual void native_start() = 0;

    virtual void native_loop() = 0;

    virtual void native_stop() = 0;
};


class runtime
{
private:
    jobject hardwaremap;
    jobject telemetry;
    jobject gamepad1;
    jobject gamepad2;

public:
    static void runtime_register(const jobject& cur_opmode);

    [[nodiscard]] const jobject& get_hardwaremap() const;
    [[nodiscard]] const jobject& get_telemetry() const;
    [[nodiscard]] const jobject& get_gamepad1() const;
    [[nodiscard]] const jobject& get_gamepad2() const;
};
