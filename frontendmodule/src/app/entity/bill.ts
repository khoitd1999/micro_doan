export interface IBill {
  id?: any;
  totalAmount?: any;
  fee?: any;
  fromDate?: any;
  toDate?: any;
  nameCli?: any;
  addressClient?: any;
  addressWarehouse?: any;
  typeShip?: any;
  idPol?: any;
  status?: any;
  carts?: any;
  idCli?: any;
  idWar?: any;
  idWare?: any;
}

export class Bill implements IBill {
  constructor(
    public id?: any,
    public totalAmount?: any,
    public fee?: any,
    public fromDate?: any,
    public toDate?: any,
    public nameCli?: any,
    public addressClient?: any,
    public addressWarehouse?: any,
    public typeShip?: any,
    public idPol?: any,
    public status?: any,
    public carts?: any,
    public idCli?: any,
    public idWar?: any,
    public idWare?: any
  ) {
  }
}
