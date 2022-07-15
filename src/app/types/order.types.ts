import { Priority, Status } from 'src/app/types/catalogs.types';
import { Client } from './client.types';
import { Pageable } from 'src/app/types/pageable.types';
import { ProjectShort } from 'src/app/types/project.types';

export interface OrderPageable extends Pageable<Order> {
}

export interface Order {
  id: number;
  code: string;
  productCode: string;
  name: string;
  quantity: number;
  orderedAt: Date;
  lot: string;
  estimatedDeliveryAt: Date;
  clientOrderCode: string;
  observation: string;
  completedAt: Date;
  canceledAt: Date;
  productionStartedAt: Date;
  cancellationReason: string;
  lastModifiedAt: Date;
  client: Client;
  status: Status;
  project: ProjectShort;
  pendingQuantity: number;
  shippedQuantity: number;
  priority: Priority;
  numberMixture?: number;
}

export interface CreateOrder {
  code: string;
  productCode: string;
  name: string;
  quantity: number;
  clientOrderCode: string;
  observation: string;
  estimatedDeliveryAt: Date;
  clientId: number;
  priorityId: number;
  projectId: number;
}

export interface UpdateOrder {
  lot: string;
  productCode: string;
  name: string;
  quantity: number;
  clientOrderCode: string;
  observation: string;
  estimatedDeliveryAt: Date;
  clientId: number;
  priorityId: number;
  projectId: number;
}

export interface CancelOrder {
  cancellationReason: string;
  statusCode: string;
}

export interface StartOrder {
  statusCode: string;
}

export interface GeneratedOrderCode {
  lastOrderCode: string;
  nextOrderCode: string;
}
