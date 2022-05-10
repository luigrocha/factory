import { animate, state, style, transition, trigger } from '@angular/animations';

export const menuItemAnimation = trigger('menuItemAnimation', [
  state('void', style({
    height: '0px',
    padding: '0px'
  })),
  state('hiddenAnimated', style({
    height: '0px',
    padding: '0px'
  })),
  state('visibleAnimated', style({
    height: '*'
  })),
  state('visible', style({
    height: '*'
  })),
  state('hidden', style({
    height: '0px',
    padding: '0px'
  })),
  transition('visibleAnimated => hiddenAnimated', animate('400ms cubic-bezier(0.86, 0, 0.07, 1)')),
  transition('hiddenAnimated => visibleAnimated', animate('400ms cubic-bezier(0.86, 0, 0.07, 1)')),
  transition('void => visibleAnimated, visibleAnimated => void',
    animate('400ms cubic-bezier(0.86, 0, 0.07, 1)'))
]);
