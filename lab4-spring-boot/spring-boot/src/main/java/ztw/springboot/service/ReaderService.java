package ztw.springboot.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ztw.springboot.repository.ReaderRepository;
import ztw.springboot.api.dto.ReaderFormDTO;
import ztw.springboot.model.Reader;
import ztw.springboot.service.interfaces.IReaderService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReaderService implements IReaderService {

    @Autowired
    private ReaderRepository readerRepository;

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public List<Reader> getReaders() {
        return readerRepository.findAll();
    }

    @Override
    public Reader getReaderById(long id) {
        return readerRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Reader with id: '" + id + "' not found")
        );
    }

    @Override
    public Reader addReader(ReaderFormDTO readerDTO) {
        return readerRepository.save(mapper.map(readerDTO, Reader.class));
    }

    @Override
    public Reader updateReader(long readerId, ReaderFormDTO readerDTO) {
        Reader reader = getReaderById(readerId);
        mapper.map(readerDTO, reader);
        return readerRepository.save(reader);
    }

    @Override
    public String deleteReader(long readerId) {
        readerRepository.deleteById(readerId);
        return "Reader with id: '" + readerId + "' deleted";
    }
}
