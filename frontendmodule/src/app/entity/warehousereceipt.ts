import {IWareHouseReceiptDetail} from "./warehousereceiptdetail";

export interface IWareHouseReceipt {
  id?: any;
  code?: any;
  idEmp?: any;
  idWar?: any;
  date?: any;
  totalAmount?: any;
  type?: any;
  wareHouseReceiptDetails?: IWareHouseReceiptDetail[];
  nameEmp?: any;
  nameWar?: any;
  idBil?: any;
  fee?: any;
}

export class WareHouseReceipt implements IWareHouseReceipt {
  constructor(
    public id?: any,
    public code?: any,
    public idEmp?: string,
    public idWar?: any,
    public date?: any,
    public totalAmount?: any,
    public type?: any,
    public wareHouseReceiptDetails?: IWareHouseReceiptDetail[],
    public nameEmp?: any,
    public nameWar?: any,
    public idBil?: any,
    public fee?: any
  ) {
  }
}
