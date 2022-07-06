export interface Pageable<T> {
  content: T[],
  pageable: PageableContent,
  last: boolean,
  totalElements: number,
  totalPages: number,
  size: number,
  number: number,
  sort: Sort,
  first: boolean
  numberOfElements: number
  empty: boolean
}

export interface PageableContent {
  sort: Sort,
  offset: number,
  pageNumber: number,
  pageSize: number,
  paged: boolean,
  unpaged: boolean,
}

export interface Sort {
  empty: boolean,
  sorted: boolean,
  unsorted: boolean
}

export interface SearchRequest {
  page: number;
  size: number;
  searchCriteria: SearchCriteria[];
  query?: string;
  filters?: string[];
}

export interface SearchCriteria {
  key: string;
  value: any;
  operation: string;
  values?: string[];
}
