import type {InsertTodo, TodoListVO, UpdateTodo} from "../../../../../type/response/dashboard/matter/todo";
import request from "@/net";
import type {BaseResponse} from "../../../../../type/response/response";
import {ElMessage} from "element-plus";

export function listTodoApi(todoAffair: string | null): Promise<TodoListVO[]> {
    // @ts-ignore
    return request.get("/user/admin/todo/listTodo?todoAffair=" + todoAffair).then((res: BaseResponse<TodoListVO[]>) => {
            if (res.code === 200)
                return res.data
            else
                ElMessage.error(res.message)
        })
}


export function insertTodoApi(insertTodo: InsertTodo) : Promise<BaseResponse<null>> {
    // @ts-ignore
    return request.post("/user/admin/todo/insertTodo", insertTodo).then((res: BaseResponse<null>) => {
        return res
    })
}

export function updateTodoApi(updateTodo : UpdateTodo) : Promise<BaseResponse<null>> {
    // @ts-ignore
    return request.put("/user/admin/todo/updateTodoById", updateTodo).then((res: BaseResponse<null>) => {
        return res
    })
}

export function deleteTodoApi(id : number)  : Promise<BaseResponse<null>> {
    // @ts-ignore
    return request.delete("/user/admin/todo/deleteTodoById/" + id).then((res: BaseResponse<null>) => {
        return res
    })
}