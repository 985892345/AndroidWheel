#!/bin/bash
. ../generate-functions.sh

# 输入模块名
read -rp "请输入模块名称 (只能包含小写字母和数字): " module_name
while ! [[ "$module_name" =~ ^[a-z][a-z0-9]+$ ]]; do
  read -rp "只能包含小写字母和数字, 请重新输入: " module_name
done

# 生成模块文件
generate_android_files "$module_name" "base/$module_name"

# 生成 build.gradle.kts, 这里单独生成, 没有使用 generate_build_gradle 函数
echo -e "plugins {
  id(\"library\")
}
publisher.artifactId = \"base-$module_name\"
android.namespace = \"com.g985892345.android.base.$module_name\"
" >"$module_name/build.gradle.kts"

# 在 settings.gradle.kts 的 // base 下面插入 include
insert_line="\/\/ base"
insert_content="include(\":base:$module_name\")"
insert_include "$insert_line" "$insert_content"

# 结果输出
GREEN='\033[0;32m'
echo -e "${GREEN}已生成 $module_name 模块"
RED='\033[0;31m'
echo -e "${RED}最后请点击右上角的大象刷新 gradle${NC}"
