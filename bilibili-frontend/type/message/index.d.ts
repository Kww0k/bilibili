export interface Message {
    to : number,
    from : number,
    text: string
}

export interface AccountMessage {
    id : number,
    username : string,
    nickname: string,
    url : string,
    signature : string
}