export interface IPolicy {
  id?: any;
  fromDis?: any;
  toDis?: any;
  amount?: any;
}

export class Policy implements IPolicy {
  constructor(
    public id?: any,
    public fromDis?: any,
    public toDis?: any,
    public amount?: any
  ) {
  }
}
