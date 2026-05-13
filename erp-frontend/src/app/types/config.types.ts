export interface Config {
  id: number;
  topbarTheme: 'dark' | 'light' | string;
  menuTheme: 'dark' | 'light' | string;
  layoutMode: 'light' | 'dark' | string;
  menuMode: 'static' | 'overlay' | 'fixed' | 'horizontal' | string;
  isRTL: boolean;
  inputStyle: 'outlined' | 'underline' | 'filled' | string;
  ripple: boolean;
  color: string
}
