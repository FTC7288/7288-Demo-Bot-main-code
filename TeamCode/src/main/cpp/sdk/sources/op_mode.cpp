#include "../header/op_mode.hpp"


void runtime::runtime_register(jobject const &cur_opmode)
{
    JNIEnv* env = getEnv();

}

const jobject &runtime::get_hardwaremap() const
{
    return hardwaremap;
}

const jobject &runtime::get_telemetry() const
{
    return telemetry;
}

const jobject &runtime::get_gamepad1() const
{
    return gamepad1;
}

const jobject &runtime::get_gamepad2() const
{
    return gamepad2;
}
