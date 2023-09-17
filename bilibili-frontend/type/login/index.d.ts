export interface Login{
    username: string,
    password: string
}

export interface Register {
    email : string,
    code : number
}

export interface AccountAuthVO {
    id: number,
    email: string,
    nickname: string,
    username: string,
    url: string,
    birthday: string,
    sex: string,
    school: string,
    signature: string
}

export interface AuthVO {
    accountAuthVO : AccountAuthVO
    token : string,
}


