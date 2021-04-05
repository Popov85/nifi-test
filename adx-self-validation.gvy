def flowFile = session.get()

//Full name of document with self-contained metadata
def metadata = flowFile.getAttribute('filename')

if (!metadata) {
	log.warn('Empty name!')
	session.putAttribute(flowFile, "failure_message", "Empty name!")
	session.putAttribute(flowFile, "failure_type", "METADATA_VALIDATION")
	session.transfer(flowFile, REL_FAILURE)
	return
}

if (metadata.length()<11) {
	log.warn('Invalid self-contained metadata!')
    session.putAttribute(flowFile, "failure_message", "Row is too short!")
	session.putAttribute(flowFile, "failure_type", "METADATA_VALIDATION")
	session.transfer(flowFile, REL_FAILURE)
	return
}

def splits = metadata.split('\\_')

def quantity = splits.size()

if (quantity!=11) {
	log.warn('Invalid metadata quantity! = {}', quantity)
	session.putAttribute(flowFile, "failure_message", "Metadata contains too few attributes!")
	session.putAttribute(flowFile, "failure_type", "METADATA_VALIDATION")
	session.transfer(flowFile, REL_FAILURE)
	return
}

//Adjust these according to the requirements!
def attrsMap = [:]
attrsMap.put("folder", splits[0])
attrsMap.put("description", splits[1])
attrsMap.put("entryType", splits[2])
attrsMap.put("policies", splits[3])
attrsMap.put("aclName", splits[4])
//TODO: temporarily hardcoded!
attrsMap.put("owner", "cortex")

flowFile = session.putAllAttributes(flowFile, attrsMap)

log.info('Success!')

session.transfer(flowFile, REL_SUCCESS)