package com.book.book.business.serviceimpl;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.book.book.business.service.DocumentService;
import com.book.book.dao.entities.Document;
import com.book.book.dao.repositories.DocumentRepository;

@Service
public class DocumentServiceImp implements  DocumentService {
    @Autowired
    DocumentRepository documentRepository;

    @Override
    public Optional<Document> getDocument(Long id){
       
      return   this.documentRepository.findById(id);
    }

    @Override
    public Document addDocument(Document document) {
        return this.documentRepository.save(document);
    }
    
    @Override
    public Document updateDocument(Document document){
       return this.documentRepository.save(document);
    }

    @Override
    public  void deleteDocument(Long id) {
          this.documentRepository.deleteById(id);
    }

    @Override
    public List<Document> getAllDocument(){
        return this.documentRepository.findAll();
    }
    
    @Override
    public List<Document> getDocumentsByCategoryId(Long categoryId){
        return documentRepository.findByCategoryId(categoryId);
    }
    
    @Override
    public List<Document> getDocumentsByAuthosId(Long authoId) {
        return documentRepository.findByAuthorId(authoId);
    }

@Override
public List<Document> searchDocumentsByKeywords(String keywords) {
    return documentRepository.findByKeywordsContaining(keywords);
}

    
}