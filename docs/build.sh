# shellcheck disable=SC2128
cd "$(dirname "$BASH_SOURCE")" || exit

python3 -m venv build

. build/bin/activate

pip install mkdocs

pip install mkdocs-plantuml

pip install mkdocs_puml

pip install plantuml-markdown

mkdocs serve