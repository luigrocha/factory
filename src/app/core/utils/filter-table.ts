import { PFilter } from 'src/app/types/filter.types';
import { SearchCriteria } from 'src/app/types/pageable.types';
import { Operations } from 'src/app/types/enums/operations';


export function getSearchCriteria(filters: PFilter[]): SearchCriteria[] {
  const searchCriteria: SearchCriteria[] = [];
  filters.forEach(filter => {
    filter.values.forEach(value => {
      if (value.value) {
        const search: SearchCriteria = {
          key: filter.key,
          value: value.value,
          operation: findOperationKey(value.matchMode)
        };
        searchCriteria.push(search);
      }
    });
  });
  return searchCriteria;
}

export function findOperationKey(operation: string): string {
  return Object.keys(Operations).find(key => Operations[key] === operation);
}
