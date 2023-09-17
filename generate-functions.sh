# 生成 android 模块以及其 api 模块都有的文件
function generate_android_files() {
  local folder_head=$1
  local folder_tail=$2
  # 创建模块文件夹
  mkdir -p "$folder_head/src/main/java/com/g985892345/android/$folder_tail"
  mkdir -p "$folder_head/src/main/res"
  # 创建杂文件
  echo "/build" > "$folder_head/.gitignore"
  # 创建 AndroidManifest.xml
  local manifest='<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
  <application>
  </application>
</manifest>'
  echo "$manifest" > "$folder_head/src/main/AndroidManifest.xml"
}

# 生成 jvm 模块以及其 api 模块都有的文件
function generate_jvm_files() {
  local folder_head=$1
  local folder_tail=$2
  # 创建模块文件夹
  mkdir -p "$folder_head/src/main/java/com/g985892345/$folder_tail"
  # 创建杂文件
  echo "/build" > "$folder_head/.gitignore"
}

# 在 settings.gradle.kts 的 line 内容后插入 content
function insert_include() {
  local line=$1
  local content=$2
  setting_file="$(dirname "$(pwd)")/settings.gradle.kts"
  if [ "$(uname -s)" = "Darwin" ]; then
    # 注意这里 sed -i 后面的空串，在 Mac 上需要单独添加
    sed -i "" "/$line/a\\
${content}
    " "$setting_file"
  else
    sed -i "/$line/a\\
${content}
    " "$setting_file"
  fi
}