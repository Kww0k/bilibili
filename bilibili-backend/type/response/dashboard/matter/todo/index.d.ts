export interface TodoListVO {
    id : number,
    todoAffair : string,
    completionStatus : string
}

export interface InsertTodo {
    todoAffair : string
}

export interface UpdateTodo {
    id : number | null,
    todoAffair : string,
    completionStatus : string
}