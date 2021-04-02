def flowFile = session.get()

//Full content of input.txt file
def content = flowFile.getAttribute('content')

if (!content) {
	log.warn('Empty row!')
    session.putAttribute(flowFile, "name", "cannot_determine")
	session.putAttribute(flowFile, "failure_message", "Empty row!")
	session.putAttribute(flowFile, "failure_type", "ROW_VALIDATION")
	session.transfer(flowFile, REL_FAILURE)
	return
}

if (content.length()<11) {
	log.warn('Invalid content!')
	session.putAttribute(flowFile, "name", "cannot_determine")
    session.putAttribute(flowFile, "failure_message", "Row is too short!")
	session.putAttribute(flowFile, "failure_type", "ROW_VALIDATION")
	session.transfer(flowFile, REL_FAILURE)
	return
}

def splits = content.split('\\^')

def quantity = splits.size()

if (quantity!=11) {
	log.warn('Invalid metadata quantity! = {}', quantity)
	session.putAttribute(flowFile, "name", "cannot_determine")
	session.putAttribute(flowFile, "failure_message", "Row contains too few attributes!")
	session.putAttribute(flowFile, "failure_type", "ROW_VALIDATION")
	session.transfer(flowFile, REL_FAILURE)
	return
}

//Adjust these according to the requirements!
def attrsMap = [:]
attrsMap.put("description", splits[0])
attrsMap.put("entryType", splits[1])
attrsMap.put("policies", splits[3])
attrsMap.put("aclName", splits[4])
//TODO: temporarily hardcoded!
attrsMap.put("owner", "cortex")

def encodedDocumentName=splits[10]

def encodedDocumentNameContainsData = encodedDocumentName?.trim()

if (!encodedDocumentNameContainsData) {
	log.warn('Document name is not specified in input.txt!')
	session.putAttribute(flowFile, "name", "cannot_determine")
	session.putAttribute(flowFile, "failure_message", "Document name is empty!")
	session.putAttribute(flowFile, "failure_type", "ROW_VALIDATION")
	session.transfer(flowFile, REL_FAILURE)
	return
}

attrsMap.put("name", encodedDocumentName)

flowFile = session.putAllAttributes(flowFile, attrsMap)

log.info('Success!')

session.transfer(flowFile, REL_SUCCESS)