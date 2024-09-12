# shellcheck disable=SC2128
cd "$(dirname "$BASH_SOURCE")" || exit

echo "$(dirname "$0")"

python3 -m venv build

. build/bin/activate

pip install mkdocs==1.6.1

pip install mkdocs-plantuml==0.1.1

pip install mkdocs_puml==1.3.1

pip install plantuml-markdown==3.10.4

# Плагин для inject-а контента файла в документ, чтобы не копировать постоянно
#pip install markdown-include==0.8.1
pip install mkdocs-macros-plugin==1.0.5
#pip install mkdocs-include-markdown-plugin==6.2.2

# Плагин для исключения папок/файлов. Например картинки или другие assets
pip install mkdocs-exclude==1.0.2


mkdocs serve