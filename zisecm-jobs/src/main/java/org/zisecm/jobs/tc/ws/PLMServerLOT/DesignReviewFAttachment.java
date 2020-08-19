package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="DesignReviewFAttachment",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="DesignReviewFAttachment",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class DesignReviewFAttachment implements java.io.Serializable{

	 	private java.lang.String fileCode;

	 	private java.lang.String fileRevision;

	 	private java.lang.String filePath;

	 	private java.lang.String fileID;

	 	private java.lang.String fileName;

	 	private int fileSize;
	    
	    
	    
		public DesignReviewFAttachment() {
		}
		
		
		public DesignReviewFAttachment(String fileCode, String fileRevision,
				String filePath, String fileID, String fileName, int fileSize) {
			this.fileCode = fileCode;
			this.fileRevision = fileRevision;
			this.filePath = filePath;
			this.fileID = fileID;
			this.fileName = fileName;
			this.fileSize = fileSize;
		}


		public java.lang.String getFileCode() {
			return fileCode;
		}
		public void setFileCode(java.lang.String fileCode) {
			this.fileCode = fileCode;
		}
		public java.lang.String getFileRevision() {
			return fileRevision;
		}
		public void setFileRevision(java.lang.String fileRevision) {
			this.fileRevision = fileRevision;
		}
		public java.lang.String getFilePath() {
			return filePath;
		}
		public void setFilePath(java.lang.String filePath) {
			this.filePath = filePath;
		}
		public java.lang.String getFileID() {
			return fileID;
		}
		public void setFileID(java.lang.String fileID) {
			this.fileID = fileID;
		}
		public java.lang.String getFileName() {
			return fileName;
		}
		public void setFileName(java.lang.String fileName) {
			this.fileName = fileName;
		}
		public int getFileSize() {
			return fileSize;
		}
		public void setFileSize(int fileSize) {
			this.fileSize = fileSize;
		} 
}
