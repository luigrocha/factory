import { TypePermission } from 'src/app/types/permission';

export function checkIfOptionIsAllowed(permissions: TypePermission[], id: number): boolean {
  if (permissions) {
    return permissions.find(permission => permission.id === id).flag;
  }
  return false;
}
