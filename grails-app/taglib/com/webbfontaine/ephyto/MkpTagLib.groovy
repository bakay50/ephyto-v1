package com.webbfontaine.ephyto

import org.grails.gsp.GroovyPagesTemplateEngine

/**
 * Copyrights 2002-2017 Webb Fontaine
 * Developer: Bakayoko Abdoulaye
 * Date: 10/07/2017
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
class MkpTagLib {

	static defaultEncodeAs = [taglib: 'raw']

	static namespace = "code"

	GroovyPagesTemplateEngine groovyPagesTemplateEngine

	def display = { attrs, body ->
		out << '<pre>'
		out << body().trim()
		out << '</pre>'
	}

	/**
	 * @attr content REQUIRED
	 */
	def render = { attrs, body ->
		out << raw(renderTemplate(attrs.content, null))
	}

	private String renderTemplate(String content, Map model) {
		String name = content.encodeAsHex() + ' ' + System.currentTimeMillis()
		def template = groovyPagesTemplateEngine.createTemplate(content, name)
		def renderedTemplate = template.make(model)
		StringWriter sw = new StringWriter()
		renderedTemplate.writeTo(sw)
		return sw.toString()
	}

	Closure searchButtons = { attrs, body ->
		if (!attrs.clear) {
			attrs.clear = 'searchClearForm()'
		}
		out << bootstrap.formActions(attrs) {
			out << bootstrap.button([type: 'success', icon: 'search', name: 'search', labelSuffix: 'label',class: 'remove-margin'])
			out << bootstrap.buttonReset([type: 'default', name: 'reset', labelSuffix: 'label', onClick: "${attrs.clear};return false"])
//			out << rowPerPage()
//			out << bootstrap.label(class: 'control-label', labelPrefix: 'search', labelSuffix: 'label', labelCode: 'row.Per.Page')
		}
	}
//	def rowPerPage = { attrs, body ->
//		if (!attrs.from) {
//			attrs.from = [10, 20, 50]
//		}
//		if (!attrs.value) {
//			attrs.value = params.max
//		}
//		if (!attrs.name) {
//			attrs.name = 'max'
//		}
//		attrs.omitEmptyOption = true
//		out << wf.select(attrs)
//	}
}
