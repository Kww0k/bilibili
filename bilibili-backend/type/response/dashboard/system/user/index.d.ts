export interface UserList {
    id: number,
    username: string,
    nickname: string,
    email: string,
    birthday: string,
    sex: string
}

export interface InsertUser {
    username: string,
    nickname: string,
    password: string,
    email: string,
}

export interface UpdateUser {
    id: number | null,
    username: string,
    nickname: string,
    email: string,
    birthday: string,
    sex: string
}

export interface UpdatePassword {
    id: number | null,
    password: string,
    email: string
}
