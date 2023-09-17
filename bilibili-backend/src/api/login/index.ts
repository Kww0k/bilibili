import type {AuthVO, Login} from "../../../type/response/login";
import request from "@/net";
import type {BaseResponse} from "../../../type/response/response";
import {ElMessage} from "element-plus";

export function loginApi(loginForm: Login): Promise<BaseResponse<AuthVO>> {
    // @ts-ignore
    return request.post("/auth/login", loginForm, {headers: {"Content-type": 'application/x-www-form-urlencoded'}}).then((res: BaseResponse<AuthVO>) => {
        return res
    })
}

