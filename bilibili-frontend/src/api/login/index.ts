import request from "@/net";
import type {BaseResponse} from "../../../type/response";
import type {AuthVO, Login, Register} from "../../../type/login";

export function loginApi(loginForm: Login): Promise<BaseResponse<AuthVO>> {
    // @ts-ignore
    return request.post("/auth/login", loginForm, {headers: {"Content-type": 'application/x-www-form-urlencoded'}}).then((res: BaseResponse<AuthVO>) => {
        return res
    })
}

export function getCodeApi(email : string) : Promise<BaseResponse<any>> {
    // @ts-ignore
    return request.get("/auth/web/account/getRegisterEmailVerifyCode?email=" + email).then((res : BaseResponse<any>) => {
        return res
    })
}

export function registerApi(registerForm : Register): Promise<BaseResponse<AuthVO>> {
    // @ts-ignore
    return request.post("/auth/web/account/registerOrLogin", registerForm).then((res: BaseResponse<AuthVO>) => {
        return res
    })
}