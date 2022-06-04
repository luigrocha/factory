import { Config } from 'src/app/types/config.types';

export const DEFAULT_CONFIG_ID = 999;


export const defaultConfig: Config = {
  id: DEFAULT_CONFIG_ID,
  topbarTheme: 'light',
  menuTheme: 'light',
  layoutMode: 'light',
  menuMode: 'static',
  isRTL: false,
  inputStyle: 'outlined',
  ripple: true,
  color: 'denim'
}

export const configs: Config[] = [
  defaultConfig
];
