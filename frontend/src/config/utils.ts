/*
 * @Author: gongxiaofei
 * @Date: 2022-06-22 13:32:07
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2022-09-23 09:29:40
 * @Description: 
 */
import { AppConfig } from '#/config'
import { OptionsConfig } from '#/optionsConfig'
import { globalConfig } from '#/globalConfig'
import { Base } from "#/globalConfig"

export function defineConfig(config: AppConfig): AppConfig {
  return config
}

export function defineOptionsConfig(config: OptionsConfig): OptionsConfig {
  return config
}

export function defineDesignerConfig(config: globalConfig): globalConfig {
  return config
}