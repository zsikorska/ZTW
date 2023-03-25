package ztw.springboot.service.interfaces;

import ztw.springboot.api.dto.ReaderFormDTO;
import ztw.springboot.model.Reader;

import java.util.List;

public interface IReaderService {

    List<Reader> getReaders();

    Reader getReaderById(long id);

    Reader addReader(ReaderFormDTO readerDTO);

    void updateReader(long readerId, ReaderFormDTO readerDTO);

    void deleteReader(long id);
}
