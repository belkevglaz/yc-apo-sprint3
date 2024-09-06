# shellcheck disable=SC2128
cd "$(dirname "$BASH_SOURCE")" || exit

python3 -m venv build

. build/bin/activate

pip install mkdocs

pip install mkdocs-plantuml

pip install mkdocs_puml

pip install plantuml-markdown

# Плагин для inject-а контента файла в документ, чтобы не копировать постоянно
pip install markdown-include

# Плагин для исключения папок/файлов. Например картинки или другие assets
pip install mkdocs-exclude


mkdocs serve