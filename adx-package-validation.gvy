import groovy.io.FileType

// Get current FlowFile
def flowFile = session.get()

// List of all files
def listAllFiles = []

// Get the path to the listenable folder
def folderPath = folder.evaluateAttributeExpressions().getValue()

def dir = new File(folderPath)
// Populate list of all files in the listenable folder
dir.eachFileRecurse (FileType.FILES) { file -> listAllFiles << file.getName()}

if (listAllFiles.size()>2 && listAllFiles.contains('input.txt')) {
	log.debug("Package is ready!")
	session.putAttribute(flowFile, "result", "package_ready")
	session.transfer(flowFile, REL_SUCCESS)
	return
}

log.debug("Package is not ready!")
session.putAttribute(flowFile, "result", "package_not_ready")

session.transfer(flowFile, REL_SUCCESS)