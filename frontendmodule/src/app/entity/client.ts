export interface IClient {
  id?: any;
  password?: any;
  fullName?: any;
  phone?: any;
}

export class Client implements IClient {
  constructor(
    public id?: any,
    public password?: any,
    public fullName?: any,
    public phone?: any
  ) {
  }
}
