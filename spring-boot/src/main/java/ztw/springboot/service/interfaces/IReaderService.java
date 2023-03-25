package ztw.springboot.service.interfaces;

import ztw.springboot.api.dto.ReaderFormDTO;
import ztw.springboot.model.Reader;

import java.util.List;

public interface IReaderService {

    List<Reader> getReaders();

    Reader getReaderById(long id);

    Reader addReader(ReaderFormDTO readerDTO);

    Reader updateReader(long readerId, ReaderFormDTO readerDTO);

    String deleteReader(long id);
}
