# -*- coding: utf-8 -*-
#
# Akka documentation build configuration file.
#

import sys, os

# -- General configuration -----------------------------------------------------

sys.path.append(os.path.abspath('../_sphinx/exts'))
extensions = ['sphinx.ext.todo', 'sphinx.ext.mathjax', 'includecode']

templates_path = ['_templates']
source_suffix = '.rst'
master_doc = 'index'
exclude_patterns = ['_build', 'pending', 'disabled']

project = u'Algebird'
copyright = u'2011-2014, Twitter Inc'
version = '@version@'
release = '@version@'

pygments_style = 'simple'
highlight_language = 'scala'
add_function_parentheses = False
show_authors = True

# -- Options for HTML output ---------------------------------------------------

html_theme = 'sphinx_rtd_theme'
html_theme_path = ['../_sphinx/themes']

html_title = 'Algebird Documentation'
html_logo = None
html_favicon = None

html_static_path = ['../_sphinx/static']

html_last_updated_fmt = '%b %d, %Y'
#html_sidebars = {}
#html_additional_pages = {}
html_domain_indices = False
html_use_index = False
html_show_sourcelink = False
html_show_sphinx = False
html_show_copyright = True
html_use_smartypants = False
html_add_permalinks = ''

# ?
htmlhelp_basename = 'algebirddoc'

html_context = {
  'include_analytics': 'online' in tags
}

# -- Options for EPUB output ---------------------------------------------------
epub_author = "Twitter Inc"
epub_language = "en"
epub_publisher = epub_author
epub_identifier = "http://algebird.github.io/docs/algebird/snapshot/"
epub_scheme = "URL"
epub_cover = ("../_sphinx/static/akka.png", "")

# -- Options for LaTeX output --------------------------------------------------

def setup(app):
  from sphinx.util.texescape import tex_replacements
  tex_replacements.append((u'⇒', ur'\(\Rightarrow\)'))

latex_paper_size = 'a4'
latex_font_size = '10pt'

latex_documents = [
  ('java', 'AkkaJava.tex', u' Akka Java Documentation',
   u'Typesafe Inc', 'manual'),
  ('scala', 'AkkaScala.tex', u' Akka Scala Documentation',
   u'Typesafe Inc', 'manual'),
]

latex_elements = {
    'classoptions': ',oneside,openany',
    'babel': '\\usepackage[english]{babel}',
    'fontpkg': '\\PassOptionsToPackage{warn}{textcomp} \\usepackage{times}',
    'preamble': '\\definecolor{VerbatimColor}{rgb}{0.935,0.935,0.935}'
    }

# latex_logo = '_sphinx/static/akka.png'
