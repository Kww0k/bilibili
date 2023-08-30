import type {
    InsertUser,
    UpdatePassword,
    UpdateUser,
    UserList
} from "../../../../../type/response/dashboard/system/user";
import {ElMessage} from "element-plus";
import type {BaseResponse} from "../../../../../type/response/response";
import request from "@/net";


export function listUserApi(username : string) : Promise<UserList[]> {
    // @ts-ignore
    return request.get("/auth/admin/account/listAccount?username=" + username).then((res : BaseResponse<UserList[]>) => {
        if (res.code === 200)
            return res.data
        else
            ElMessage.error(res.message)
    })
}

export function insertUserApi(insertUser : InsertUser) : Promise<BaseResponse<null>> {
    // @ts-ignore
    return request.post("/auth/admin/account/insertAccount", insertUser).then((res : BaseResponse<null>) => {
        return res
    })
}

export function updateUserApi(updateUser : UpdateUser) : Promise<BaseResponse<null>> {
    // @ts-ignore
    return request.put("/auth/admin/account/updateAccountById", updateUser).then((res : BaseResponse<null>) => {
        return res
    })
}

export function updatePasswordApi(updatePassword : UpdatePassword) : Promise<BaseResponse<null>> {
    console.log(updatePassword)
    // @ts-ignore
    return request.put("/auth/admin/account/updateAccountById", updatePassword).then((res : BaseResponse<null>) => {
        return res
    })
}

export function deleteUserApi(id : number) : Promise<BaseResponse<null>> {
    console.log(id)
    // @ts-ignore
    return request.delete("/auth/admin/account/deleteAccountById/" + id).then((res : BaseResponse<null>) => {
        return res
    })
}